package com.fc.vo;

import com.fc.entity.TbNote;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: T21
 * @date: 2022/5/8.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteVo extends TbNote {
    private Integer typeId;
    private String groupName;
    private Integer noteCount;
    private String typeName;
}
