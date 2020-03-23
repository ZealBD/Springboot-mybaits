import com.hesicare.HealthApplication;
import com.hesicare.health.dao.PressureDao;
import com.hesicare.health.dao.Testss;
import com.hesicare.health.services.impl.Testccimpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthApplication.class)
public class TestC {
    @Autowired
    //private Testss testss ;
    private Testccimpl testccimpl;
    //private PressureDao testss;
    //private TripartiteDeviceMapper dao;
    @Test
    public void testSelect() {
      //testss.selectList(null);
        System.out.println(testccimpl.sel().size());
     //   System.out.println(dao.selectById(101));
    }

}