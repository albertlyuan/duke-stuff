import numpy as np
import os
import json
import unittest
from gradescope_utils.autograder_utils.decorators import weight
from gradescope_utils.autograder_utils.decorators import partial_credit
from gradescope_utils.autograder_utils.files import check_submitted_files
from get_sid import get_sid


def run_test(name, in_file, sid):
    retc = os.system("./run_target.sh " + sid + " " + name + " " + in_file)
    if retc != 0:
        print('Encountered Non-Zero return code ({0})'.format(retc))
    f = open("out.txt")
    ret = f.readlines()
    f.close()
    os.system("rm out.txt")
    if len(ret) == 0:
        return ''
    return ret[0]


def read_file(in_file):
    f = open(in_file, 'r')
    ret = ' '.join(f.readlines())
    f.close()
    if len(ret) == 0:
        return ''
    return ret


class RunTest (unittest.TestCase):
    @weight(0)
    def test_submitted_files(self):
        """Check submitted files"""
        missing_files = check_submitted_files(['exploit1.txt', 'exploit2.txt', 'exploit2.raw'])
        for path in missing_files:
            print('Missing {0}'.format(path))
        self.assertEqual(len(missing_files), 0, 'Missing some required files! Make sure you submit \'exploit1.txt\','
                                                '\'exploit2.txt\', and \'exploit2.raw\'. Otherwise, our autograder'
                                                'won\'t work!')

    """
    40 pts target1
        - 20% fully functional = 8 pts
        - 80% partial credit = 32 pts
            > measure length of padding
    60 pts target2
        - 20% fully functional = 12 pts
        - 80% partial credit = 48 pts
            > 33% credit = 16 pts = correct address
            > 66% credit = 32 pts = correct padding
    """
    @partial_credit(100)
    def test_all(self, set_score=None):
        """Attack Project Run"""
        def get_t1_grade(sid):
            print("Testing Target1 for NetID: {0}".format(sid))
            bypass = run_test("target1", "exploit1.txt", sid)
            if bypass == "Success! You correctly hacked the program\n":
                return 40
            else:
                print("Success message not found from Target1 execution")
            buff = read_file("exploit1.txt")
            payload = "Use the force Luke"
            cnt = buff.find(payload)
            if cnt == -1:
                print("Correct payload not found")

            # Don't give partial credit if they don't have the payload at the end of the string
            if cnt + len(payload) != len(buff):
                return 0
            else:
                correct = read_file("targets/" + sid + "/exploit1.txt").find(payload)
                diff = max(abs(correct - cnt), 1) - 1
                # Exponential decay
                # diff = 0,1 => 100% partial
                # diff = 2 => 66% partial
                # diff = 3 => 45% partial
                # diff = 4 => 30% partial
                # ...
                return np.round(32.0 * np.exp(-0.4 * diff), 2)

        def get_t2_grade(sid):
            """ Target2 partial credit"""
            print("\nTesting Target2 for NetID: {0}".format(sid))
            def extract_zeroes_and_payload(strar, verbose=False):
                if strar[-1] == '0a':
                    strar = strar[:-1]

                filler = strar[0]
                if verbose:
                    print("We're guessing that your filler is repeated '{0}'s".format(filler))
                while strar[-1].strip() == "00" or strar[-1].strip() == filler:
                    strar = strar[:-1]
                acc = ""
                payload = strar
                if len(strar) == 0:
                    return 0, ''
                while payload[0] == '00' or payload[0] == filler:
                    payload = payload[1:]
                for p in payload:
                    acc = acc + p
                acc = acc.strip()
                for i in range(len(strar)):
                    if strar[i] == payload[0]:
                        return i * 2, acc
                return 0, acc

            bypass = run_test("target2", "exploit2.raw", sid)
            if bypass == "Success! You hacked the program!\n":
                return 60
            else:
                print("Did not find success message from Target2")
            buff = read_file("targets/" + sid + "/exploit2.txt").split(' ')
            correct_zeroes, correct_payload = extract_zeroes_and_payload(buff)

            buff = read_file("exploit2.txt").split(' ')
            try:
                student_zeroes, student_payload = extract_zeroes_and_payload(buff, verbose=True)
            except Exception as e:
                print(e)
                print("Something went wrong... This _may_ be the autograder's fault. Please post to Ed if this happens."
                      "Ensure that your exploit1.txt is a single line of input.")
                return 0
            score = 0
            if student_payload == correct_payload:
                score += 16
                print("Your payload is correct.")
            else:
                print("Your payload is incorrect. Our automated tools thing your payload is: '{0}' (If you think this isn't the case, please use 0s for padding in case you aren't already.".format(student_payload))
            zero_diff = max(np.abs(correct_zeroes - student_zeroes), 2)-2
            if zero_diff != 0:
                print("The amount of padding may be wrong.")
            else:
                print("The amount of padding is correct.")
            print("If your padding and payload are correct, have you submitted with the updated .raw?")
            return score + np.round((32.0 * np.exp(-.2 * zero_diff)), 2)

        sids = get_sid()
        print("Grading the submission using NetID: {0}. If this is not what you intend, make sure the person with"
              "the desired NetID submits the project to Gradescope.".format(sids[0]))
        score = get_t1_grade(sids[0]) + get_t2_grade(sids[0])
        set_score(score)
