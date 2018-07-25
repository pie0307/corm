package pro.pie.me.test;

import com.google.common.collect.Lists;
import pro.pie.me.corm.CService;
import pro.pie.me.corm.model.Condition;
import pro.pie.me.corm.model.OrderBy;
import pro.pie.me.corm.model.Page;
import pro.pie.me.dao.itf.IDataAccess;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.pie.me.generate.TableInfo;
import pro.pie.me.generate.TableStructureUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : liuby.
 * Description :
 * Date : Created in 2018/7/25 15:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Resource
    private IDataAccess dataAccess;
    @Resource
    private CService cService;

    @Test
    public void test() {
        List<Condition> conditionList = Lists.newArrayList();
        Page<ExamResultModel> examResultModelPage = dataAccess.queryByPage(ExamResultModel.class, conditionList,
                Lists.newArrayList(OrderBy.desc("importTime"), OrderBy.desc("publishTime")),
                1, 20);

        System.out.println();
    }

    @Test
    public void test1() {
        ZrChangeEntity r = new ZrChangeEntity();
        r.setChangeName("刘丙雨测试");
        cService.save(r);
        Page<ResblockEntity> page = cService.findPage(ResblockEntity.class, 1, 10);
        System.out.println();
    }

    public static void main(String[] args) {
        TableInfo tableInfo = TableStructureUtil.getTableInfo(ExamResultModel.class);
        System.out.println(TableStructureUtil.toSql(tableInfo));
    }
}
