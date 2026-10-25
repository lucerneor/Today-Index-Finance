package org.itheima.stock.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.itheima.stock.mapper.SysUserMapper;
import org.itheima.stock.pojo.entity.SysUser;
import org.itheima.stock.service.UserService;
import org.itheima.stock.vo.req.LoginReqVo;
import org.itheima.stock.vo.resp.LoginRespVo;
import org.itheima.stock.vo.resp.R;
import org.itheima.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 定义用户服务实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.findUserInfoByUserName(userName);
    }

    /**
     * 用户登录功能
     * @param vo
     * @return
     */
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
//        1.判断参数是否合法
        if(vo==null || StringUtils.isBlank(vo.getUsername()) ||StringUtils.isBlank(vo.getPassword())||StringUtils.isBlank(vo.getCode())){
            return R.error(ResponseCode.DATA_ERROR);
        }
//        2.根据用户名去数据库查询用户信息，获取密码的密文
        SysUser dbUser = sysUserMapper.findUserInfoByUserName(vo.getUsername());
        if (dbUser == null){
//            用户名不存在
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
//        3.调用密码匹配器匹配输入的明文密码和数据库的密文密码
        if (!passwordEncoder.matches(vo.getPassword(),dbUser.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
//        4.响应
        LoginRespVo respVo = new LoginRespVo();
        respVo.setUsername(dbUser.getUsername());
        respVo.setNickName(dbUser.getNickName());
        respVo.setPhone(dbUser.getPhone());
        respVo.setId(dbUser.getId());
//        发现LoginRespVo与SysUser对象属性名称和类型一致
//        必须保证属性名称和类型一致
        BeanUtils.copyProperties(dbUser,respVo);
        return R.ok(respVo);
    }
}
