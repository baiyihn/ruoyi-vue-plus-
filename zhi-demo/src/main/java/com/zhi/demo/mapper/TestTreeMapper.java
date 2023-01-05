package com.zhi.demo.mapper;

import com.zhi.common.annotation.DataColumn;
import com.zhi.common.annotation.DataPermission;
import com.zhi.common.core.mapper.BaseMapperPlus;
import com.zhi.demo.domain.TestTree;
import com.zhi.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVo> {

}
