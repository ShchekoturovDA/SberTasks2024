import org.junit.Assert;
import org.junit.Test;
import org.sber.DataContainer;
import org.sber.IO;
import org.sber.otdel;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Testing {

    @Test
    public void TestOtdNig() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        otdel test = new otdel();
        DataContainer dc = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        DataContainer dc2 = dc;
        test.exec(5, 5, dc);
        dc2.setAge(25);
        dc2.setPasslvl(0);
        Assert.assertEquals(dc2, dc);

        test.exec(5, 2, dc);
        dc2.setName("Default");
        Assert.assertEquals(dc2, dc);

    }

    @Test
    public void TestOtdOth() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        otdel test = new otdel();
        DataContainer dc = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        DataContainer dc2 = dc;
        test.exec(6, 5, dc);

        Assert.assertEquals(new DataContainer("Average Worker #953", 43, "Lukyan", 1.9, 1), dc);

        test.exec(6, 3, dc);
        dc2.setName("Default");
        Assert.assertEquals(dc2, dc);

    }

    @Test
    public void CountTest() throws InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        IO io = new IO();
        io.createLog();
        otdel test = new otdel();
        DataContainer dc = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        test.exec(6, 3, dc);
        io.writeFile(dc.toString());
        Thread.sleep(1000);
        test.exec(6, 3, dc);
        io.writeFile(dc.toString());
        Thread.sleep(1000);
        test.exec(6, 3, dc);
        Thread.sleep(1000);
        io.writeFile(dc.toString());
        Assert.assertEquals(2, (new File("log")).listFiles().length);

    }
}
