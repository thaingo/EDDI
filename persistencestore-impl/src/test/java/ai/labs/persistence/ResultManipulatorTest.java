package ai.labs.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ginccc
 */
class ResultManipulatorTest {

    private ResultManipulator<TestEntity> resultManipulator;

    @BeforeEach
    void setup() {
        List<TestEntity> testEntities = new LinkedList<>();
        testEntities.add(new TestEntity("something4"));
        testEntities.add(new TestEntity("something2"));
        testEntities.add(new TestEntity("something1"));
        testEntities.add(new TestEntity("something3"));
        testEntities.add(new TestEntity("whatever2"));
        testEntities.add(new TestEntity("whatever1"));

        resultManipulator = new ResultManipulator<>(testEntities, TestEntity.class);
    }

    @Test
    void testConstructor1() {
        try {
            new ResultManipulator<>(null, TestEntity.class);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testConstructor2() {
        try {
            new ResultManipulator<>(new LinkedList<>(), null);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testFilter() {
        try {
            resultManipulator.filterEntities(null);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testFilter1() throws ResultManipulator.FilterEntriesException {
        //setup
        String filter = "what";

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{new TestEntity("whatever2"), new TestEntity("whatever1")};

        //test
        resultManipulator.filterEntities(filter);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testFilter2() throws ResultManipulator.FilterEntriesException {
        //setup
        String filter = "\"something2\"";

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{new TestEntity("something2")};

        //test
        resultManipulator.filterEntities(filter);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testFilter3() throws ResultManipulator.FilterEntriesException {
        //setup
        String filter = "";

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("something4"),
                new TestEntity("something2"),
                new TestEntity("something1"),
                new TestEntity("something3"),
                new TestEntity("whatever2"),
                new TestEntity("whatever1")};

        //test
        resultManipulator.filterEntities(filter);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testFilter4() throws ResultManipulator.FilterEntriesException {
        //setup
        String filter = "somethingwichisnotinthelist";

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{};

        //test
        resultManipulator.filterEntities(filter);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testSort() {
        try {
            resultManipulator.sortEntities(null, "");
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testSort2() {
        try {
            resultManipulator.sortEntities((o1, o2) -> {
                return 0;  //not implemented,
            }, null);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testSort3() {
        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("something1"),
                new TestEntity("something2"),
                new TestEntity("something3"),
                new TestEntity("something4"),
                new TestEntity("whatever1"),
                new TestEntity("whatever2")};

        //test
        resultManipulator.sortEntities(Comparator.comparing(TestEntity::getTest), ResultManipulator.ASCENDING);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testSort4() {
        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("whatever2"),
                new TestEntity("whatever1"),
                new TestEntity("something4"),
                new TestEntity("something3"),
                new TestEntity("something2"),
                new TestEntity("something1")
        };

        //test
        resultManipulator.sortEntities(Comparator.comparing(TestEntity::getTest), ResultManipulator.DESCENDING);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testLimit1() {
        //setup
        int index = 0;
        int limit = 5;

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("something4"),
                new TestEntity("something2"),
                new TestEntity("something1"),
                new TestEntity("something3"),
                new TestEntity("whatever2")
        };

        //test
        this.resultManipulator.limitEntities(index, limit);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testLimit2() {
        //setup
        int index = 2;
        int limit = 2;

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("whatever2"),
                new TestEntity("whatever1")};

        //test
        this.resultManipulator.limitEntities(index, limit);

        //assert
        Assertions.assertEquals(testEntities.length, resultManipulator.getManipulatedList().size());
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testLimit3() {
        //setup
        int index = 10;
        int limit = 3;

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{};

        //test
        this.resultManipulator.limitEntities(index, limit);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testLimit4() {
        //setup
        int index = 0;
        int limit = 0;

        //setup expected
        TestEntity[] testEntities = new TestEntity[]{
                new TestEntity("something4"),
                new TestEntity("something2"),
                new TestEntity("something1"),
                new TestEntity("something3"),
                new TestEntity("whatever2"),
                new TestEntity("whatever1")};

        //test
        this.resultManipulator.limitEntities(index, limit);

        //assert
        Assertions.assertArrayEquals(testEntities, resultManipulator.getManipulatedList().toArray());
    }

    @Test
    void testLimit5() {
        //setup
        int index = -1;
        int limit = 0;

        //test
        try {
            this.resultManipulator.limitEntities(index, limit);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testLimit6() {
        //setup
        int index = 0;
        int limit = -1;

        //test
        try {
            this.resultManipulator.limitEntities(index, limit);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    public class TestEntity {
        private String test;

        private TestEntity(String test) {
            this.test = test;
        }

        public String getTest() {
            return test;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestEntity that = (TestEntity) o;

            return test.equals(that.test);

        }

        @Override
        public int hashCode() {
            return test.hashCode();
        }

        @Override
        public String toString() {
            return "TestEntity{" +
                    "test='" + test + '\'' +
                    '}';
        }
    }
}
