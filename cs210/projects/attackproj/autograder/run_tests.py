import unittest
from gradescope_utils.autograder_utils.json_test_runner import JSONTestRunner

resfile = open("/autograder/results/results.json", 'w')
suite = unittest.defaultTestLoader.discover("tests")
JSONTestRunner(stream=resfile).run(suite)
resfile.close()

