import unittest
import os
from gradescope_utils.autograder_utils.decorators import weight
from gradescope_utils.autograder_utils.decorators import partial_credit
from gradescope_utils.autograder_utils.files import check_submitted_files
from test_checks import *
import subprocess


class RunTest (unittest.TestCase):
    @weight(0)
    def test_submitted_files(self):
        """Check submitted files"""
        missing_files = check_submitted_files(['barrier.c', 'barrier.h'])
        for path in missing_files:
            print('Missing {0}'.format(path))
        self.assertEqual(len(missing_files), 0, 'Missing some required files! Make sure to submit barrier.c and barrier.h')

    """
    30 pts single user barrier test
    30 pts barrier re-use test
    40 pts application test
    """
    @partial_credit(100)
    def test_all(self, set_score=None):
        buildsuccess = os.system("make")
        if not buildsuccess:
            print("Failed to build.")
            set_score(0)
            return
        for task in ['-sbt', '-brt', '-app']:
            try:
                subprocess.run(['./barriest_test', task], timeout=30)
            except Exception:
                print("{0} likely timed out... This may indicate a deadlock in your code.".format(task));
        res = check_tests()
        score = 0
        if res[0] == False:
            print("Single use barrier test (sbt) failed")
        else:
            score = score + 30

        if res[1] == False:
            print("Barrier Re-use test (brt) failed")
        else:
            score = score + 30

        if res[2] == False:
            print("Application test failed")
        else:
            score = score + 40

        set_score(score)

