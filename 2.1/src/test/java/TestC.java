import com.hesicare.HealthApplication;
import com.hesicare.health.mappering.TripartiteDeviceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthApplication.class)
public class TestC {
    @Autowired
    private TripartiteDeviceMapper dao;

    @Test
    public void testSelect() {
        System.out.println(dao.sela());
        System.out.println(dao.selaa());
    }
}