package com.netflix.loadbalancer;

import java.util.List;

/**
 * Interface that defines the operations for a software loadbalancer. A typical
 * loadbalancer minimally need a set of servers to loadbalance for, a method to
 * mark a particular server to be out of rotation and a call that will choose a
 * server from the existing list of server.
 * 
 * @author stonse
 * 
 */
public interface ILoadBalancer {

	/**
	 * // 初始化服务列表
	 * Initial list of servers.
	 * This API also serves to add additional ones at a later time
	 * The same logical server (host:port) could essentially be added multiple times
	 * (helpful in cases where you want to give more "weightage" perhaps ..)
	 * 
	 * @param newServers new servers to add
	 */
	public void addServers(List<Server> newServers);
	
	/**
	 * // 从列表中选择一个服务
	 * Choose a server from load balancer.
	 * 
	 * @param key An object that the load balancer may use to determine which server to return. null if 
	 *         the load balancer does not use this parameter.
	 * @return server chosen
	 */
	public Server chooseServer(Object key);
	
	/**
	 * // 标记服务为down
	 * To be called by the clients of the load balancer to notify that a Server is down
	 * else, the LB will think its still Alive until the next Ping cycle - potentially
	 * (assuming that the LB Impl does a ping)
	 * 
	 * @param server Server to mark as down
	 */
	public void markServerDown(Server server);
	
	/**
	 * // 获取up和reachable的服务
	 * @deprecated 2016-01-20 This method is deprecated in favor of the
	 * cleaner {@link #getReachableServers} (equivalent to availableOnly=true)
	 * and {@link #getAllServers} API (equivalent to availableOnly=false).
	 *
	 * Get the current list of servers.
	 *
	 * @param availableOnly if true, only live and available servers should be returned
	 */
	@Deprecated
	public List<Server> getServerList(boolean availableOnly);

	/**
	 * @return Only the servers that are up and reachable.
     */
    public List<Server> getReachableServers();

    /**
	 * 获取所有服务（reachable and unreachable）
     * @return All known servers, both reachable and unreachable.
     */
	public List<Server> getAllServers();
}
