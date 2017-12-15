java_library(
    name = "Reference",
    srcs = ["ReferencedFile.java"],
)

java_test(
    name = "WithReferenceTest",
    srcs = ["WithReferenceTest.java"],
    test_class = "foo.WithReferenceTest",
    deps = ["@junit//jar", ":Reference"],
)

java_test(
    name = "NoReferenceTest",
    srcs = ["NoReferenceTest.java"],
    test_class = "foo.NoReferenceTest",
    deps = ["@junit//jar"],
)
