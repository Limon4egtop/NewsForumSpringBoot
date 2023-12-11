package ru.limon4etop.SpringBoot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTests {
    @Test
    void getterNullTest() {
        Student student = new Student();
        Assert.assertEquals(null, student.getName());
        Assert.assertEquals(null, student.getNumber());
    }

    @Test
    void getterNotNullTest() {
        Student student = new Student("Name", 2);
        Assert.assertEquals("Name", student.getName());
        Integer a = 2;
        Assert.assertEquals(a, student.getNumber());
    }

    @Test
    void setterNotNullTest() {
        Student student = new Student();
        student.setName("Strrrr");
        Integer testNumber = 3;
        student.setNumber(testNumber);
        Assert.assertEquals("Strrrr", student.getName());
        Assert.assertEquals(testNumber, student.getNumber());
    }

    @Test
    void pluseNumToNumberTest(){
        Student student = new Student("Name", 4);
        Integer otvOfPluse = 6;
        Assert.assertEquals(otvOfPluse, student.pluseNumToNumber(2));
    }
}
