package cn.kgc.tangcco.service;


import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 下午1:11
 */
public interface UserinfoService {
    /**
     * 通过uuid查询Person
     * @param map
     * @return map
     * @throws Exception
     */
    public Map<String, Object> findUserInfo(Map<String, Object> map) throws Exception;

    /**
     * 执行修改密码
     * @param map
     * @return map
     * @throws Exception
     */
    public Map<String, Object> setPassword(Map<String, Object> map) throws Exception;

    /**
     * 修改基本信息
     * @param map
     * @return map
     * @throws Exception
     */
    public Map<String, Object> setInfo(Map<String, Object> map) throws Exception;
}
