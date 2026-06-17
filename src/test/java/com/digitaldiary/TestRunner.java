package com.digitaldiary;

public class TestRunner {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        for (TestCase test : new TestCase[] {
                new TestCase("Repository CRUD and search", DiaryRepositoryTest::crudAndSearch),
                new TestCase("Controller validation", DiaryControllerTest::validationRejectsBlankTitle),
                new TestCase("Observer notification", DiaryControllerTest::observerReceivesRefresh)
        }) {
            try {
                test.runnable().run();
                passed++;
                System.out.println("PASS " + test.name());
            } catch (Throwable throwable) {
                failed++;
                System.out.println("FAIL " + test.name() + " - " + throwable.getMessage());
            }
        }

        System.out.println("Tests passed: " + passed + ", failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private record TestCase(String name, Runnable runnable) {
    }
}
