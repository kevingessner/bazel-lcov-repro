# Repro for LcovMerger bug in Bazel

`bazel test //...` passes for both tests, but running `bazel coverage` only passes for one of them.

`WithReferenceTest` refers to another Java source file, and correctly generates a valid `coverage.dat` file:

```
> bazel coverage :WithReferenceTest
bazel coverage :WithReferenceTest --test_output=errors --nocache_test_results
INFO: Analysed target //:WithReferenceTest (0 packages loaded).
INFO: Found 1 test target...
Target //:WithReferenceTest up-to-date:
  bazel-bin/WithReferenceTest.jar
  bazel-bin/WithReferenceTest
INFO: Elapsed time: 1.205s, Critical Path: 0.72s
INFO: Build completed successfully, 2 total actions
//:WithReferenceTest                                                     PASSED in 0.7s
  /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/execroot/__main__/bazel-out/k8-fastbuild/testlogs/WithReferenceTest/coverage.dat
```

But `NoReferenceTest`, which doesn't reference any non-test sources, fails in `LcovMerger`:

```
> bazel coverage :NoReferenceTest --test_output=errors
INFO: Analysed target //:NoReferenceTest (0 packages loaded).
INFO: Found 1 test target...
FAIL: //:NoReferenceTest (see /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/test.log)
INFO: From Testing //:NoReferenceTest:
==================== Test output for //:NoReferenceTest:
+ [[ -z bazel-out/k8-fastbuild/bin/external/bazel_tools/tools/test/LcovMerger ]]
+ COVERAGE_LEGACY_MODE=
+ [[ -z bazel-out/k8-fastbuild/bin/NoReferenceTest.instrumented_files ]]
+ ROOT=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__
+ [[ bazel-out/k8-fastbuild/bin/NoReferenceTest.instrumented_files != /* ]]
+ export COVERAGE_MANIFEST=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/bin/NoReferenceTest.instrumented_files
+ COVERAGE_MANIFEST=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/bin/NoReferenceTest.instrumented_files
+ export COVERAGE_DIR=_coverage/NoReferenceTest/test
+ COVERAGE_DIR=_coverage/NoReferenceTest/test
+ [[ _coverage/NoReferenceTest/test == /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__* ]]
+ COVERAGE_DIR=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test
+ mkdir -p /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test
+ COVERAGE_OUTPUT_FILE=bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat
+ [[ bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat == /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__* ]]
+ COVERAGE_OUTPUT_FILE=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat
+ export JAVA_COVERAGE_FILE=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test/jvcov.dat
+ JAVA_COVERAGE_FILE=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test/jvcov.dat
+ export COVERAGE=1
+ COVERAGE=1
+ export BULK_COVERAGE_RUN=1
+ BULK_COVERAGE_RUN=1
+ [[ ! -n '' ]]
+ for name in '"$LCOV_MERGER"'
+ [[ ! -e bazel-out/k8-fastbuild/bin/external/bazel_tools/tools/test/LcovMerger ]]
+ [[ -n '' ]]
+ cd /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/bin/NoReferenceTest.runfiles/__main__
+ /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/bin/NoReferenceTest.runfiles/__main__/NoReferenceTest
JUnit4 Test Runner
.
Time: 0.01

OK (1 test)


BazelTestRunner exiting with a return value of 0
JVM shutdown hooks (if any) will run now.
The JVM will exit once they complete.

-- JVM shutdown starting at 2017-12-15 16:15:32 --

+ TEST_STATUS=0
+ touch /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat
+ [[ 0 -ne 0 ]]
+ cd /home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__
+ [[ -n '' ]]
+ export 'LCOV_MERGER_CMD=bazel-out/k8-fastbuild/bin/external/bazel_tools/tools/test/LcovMerger --coverage_dir=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test --output_file=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat'
+ LCOV_MERGER_CMD='bazel-out/k8-fastbuild/bin/external/bazel_tools/tools/test/LcovMerger --coverage_dir=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test --output_file=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat'
+ [[ -n '' ]]
+ JAVA_RUNFILES=
+ exec bazel-out/k8-fastbuild/bin/external/bazel_tools/tools/test/LcovMerger --coverage_dir=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/_coverage/NoReferenceTest/test --output_file=/home/kgessner/.cache/bazel/_bazel_kgessner/ad57d61feb27429aeb51ab805d67f76e/bazel-sandbox/8161754637406086809/execroot/__main__/bazel-out/k8-fastbuild/testlogs/NoReferenceTest/coverage.dat
Dec 15, 2017 4:15:32 PM com.google.devtools.lcovmerger.LcovMerger merge
SEVERE: No lcov file found.
```

The `coverage.dat` for `NoReferenceTest` exists but is empty.  `LcovMerger` should not fail in such a case but also generate an empty, valid file.
