import org.junit.Assert;
import org.junit.Test;
import org.sber.DataContainer;
import org.sber.IO;
import org.sber.Executor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Testing {

    @Test
    public void testScheduleNight() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Executor test = new Executor();
        DataContainer dataContainer = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        DataContainer dataContainer2 = dataContainer;
        test.chooseAction(5, 5, dataContainer);
        dataContainer2.setAge(25);
        dataContainer2.setPassLevel(0);
        Assert.assertEquals(dataContainer2, dataContainer);
        test.chooseAction(5, 2, dataContainer);
        dataContainer2.setName("Default");
        Assert.assertEquals(dataContainer2, dataContainer);
    }

    @Test
    public void testScheduleOther() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Executor test = new Executor();
        DataContainer dataContainer = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        DataContainer dataContainer2 = dataContainer;
        test.chooseAction(6, 5, dataContainer);
        Assert.assertEquals(new DataContainer("Average Worker #953", 43, "Lukyan", 1.9, 1), dataContainer);
        test.chooseAction(6, 3, dataContainer);
        dataContainer2.setName("Default");
        Assert.assertEquals(dataContainer2, dataContainer);
    }

    @Test
    public void countTest() throws InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        IO io = new IO();
        io.createLog();
        Executor test = new Executor();
        DataContainer dataContainer = new DataContainer("Vasil", 43, "Lukyan", 1.9, 5);
        test.chooseAction(6, 3, dataContainer);
        io.writeFile(dataContainer.toString());
        Thread.sleep(1000);
        test.chooseAction(6, 3, dataContainer);
        io.writeFile(dataContainer.toString());
        Thread.sleep(1000);
        test.chooseAction(6, 3, dataContainer);
        Thread.sleep(1000);
        io.writeFile(dataContainer.toString());
        Assert.assertEquals(2, (new File("log")).listFiles().length);
    }
}
