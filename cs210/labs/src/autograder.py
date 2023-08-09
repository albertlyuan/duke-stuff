"""
Autograder for executable, SPIM, and Logisim files.
Uses provided settings file to grade assignment.
"""

import os
import argparse
import time
import json
from common import Grader


def main():
    """
    Parse arguments and run autograder.
    """
    parser = argparse.ArgumentParser(description="Run autograder.")
    parser.add_argument("test_suite", type=str, default="ALL", help="ALL, CLEAN, or TEST_SUITE_NAME")
    parser.add_argument("--settings", type=str, default="settings.json",
                        help="settings file to use for grading (default=settings.json)")

    args = parser.parse_args()
    grader = Grader(args.test_suite, args.settings)

    start_time = time.time()
    test_outputs, total_score = grader.run()
    end_time = time.time()

    output_text = "Some test cases may be hidden until after the late deadline has passed.\n" \
                  + "NOTE: late penalties as well as certain penalties regarding disallowed components and " \
                  + "code are not applied by the autograder and will be applied manually before the final " \
                  + "grade is posted on Sakai."

    if os.path.exists("compile_output.txt"):
        with open("compile_output.txt", "r") as compile_output_file:
            compile_output = compile_output_file.read()
        output_text += "\n\n" + "###### COMPILE OUTPUT ######\n" + compile_output

    output = {
        "score": total_score,
       "stdout_visibility": "hidden",
        "output": output_text,
        "tests": [test_output.to_dictionary() for test_output in test_outputs],
        "execution_time": end_time - start_time
    }

    with open("results.json", "w+") as result_file:
        json.dump(output, result_file)

if __name__ == "__main__":
    main()

