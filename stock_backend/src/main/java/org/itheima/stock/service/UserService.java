package org.itheima.stock.service;


import org.itheima.stock.pojo.entity.SysUser;
import org.itheima.stock.vo.req.LoginReqVo;
import org.itheima.stock.vo.resp.LoginRespVo;
import org.itheima.stock.vo.resp.R;

// 定义用户服务接口
public interface UserService {
    /**
     * 根据用户名称查询用户信息
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);

    /**
     * 用户登录功能
     * @param vo
     * @return
     */
    R<LoginRespVo> login(LoginReqVo vo);
}
