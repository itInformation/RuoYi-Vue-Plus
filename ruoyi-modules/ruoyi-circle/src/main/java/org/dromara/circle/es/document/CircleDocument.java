package org.dromara.circle.es.document;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:44 2025/5/2
 * modified by
 */
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Data
@Document(indexName = "circle_group") // 索引名称
public class CircleDocument {

    @Id
    private Long id;

    // 圈子名称（使用 ik_analyzer 分词器）
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_analyzer",name = "group_name"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String groupName;

    // 描述（使用 ik_analyzer 分词器）
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "ik_analyzer"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String description;

    // 封面图（保留 keyword 类型）
    @MultiField(
        mainField = @Field(type = FieldType.Text,name = "cover_img"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String coverImg;

    // 其他字段
    @Field(type = FieldType.Long,name = "create_by")
    private Long createBy;

    @Field(
        name = "update_time",
        type = FieldType.Date,
        format = {},
        pattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
    )
    private Date createTime;

    @Field(type = FieldType.Long)
    private Long deleted;

    @Field(type = FieldType.Long,name = "group_count")
    private Long groupCount;

    @Field(type = FieldType.Long,name = "join_mode")
    private Long joinMode;

    @Field(type = FieldType.Long,name = "owner_id")
    private Long ownerId;

    @Field(type = FieldType.Long,name = "recycle_bin")
    private Long recycleBin;

    @Field(type = FieldType.Long)
    private Long review;

    @Field(type = FieldType.Long)
    private Long status;

    // 租户ID（text + keyword）
    @MultiField(
        mainField = @Field(type = FieldType.Text,name = "tenant_id"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String tenantId;

    @Field(type = FieldType.Long,name = "update_by")
    private Long updateBy;

    @Field(
        name = "update_time",
        type = FieldType.Date,
        format = {},
        pattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
    )
    private Date updateTime;
}
