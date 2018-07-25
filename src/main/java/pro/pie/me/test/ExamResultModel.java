package pro.pie.me.test;

import pro.pie.me.corm.annotation.Comment;
import pro.pie.me.corm.annotation.Table;
import pro.pie.me.corm.model.SuperModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 考试结果
 * 考试  考试结果 人员
 **/
@Getter
@Setter
@ToString
@Table("t_culture_exam_result")
public class ExamResultModel extends SuperModel {

    @Comment("学员编码 系统号")
    @Min(1)
    private String userCode;
    @Comment("学员name")
    private String userName;
    @Comment("考试Id")
    @Min(1)
    private long examId;
    private String examCode;
    @Comment("考试名称")
    @Length(max = 255)
    private String examName;
    @Comment("分数")
    private double score;
    @Comment("考试是否通过 Y=是 N=否")
    private String isPass;
    @Comment("是否作废  Y=是 N=否")
    private String isInValid = "N";
    @Comment("是否公布 Y=是 N=否")
    private String isPublish = "N";
    @Comment("导入时间")
    private Date importTime;
    @Comment("发布时间")
    private Date publishTime;
    @Comment("导入人")
    private String importer;
}
