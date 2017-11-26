package cn.clubox.quiz.web.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeLocalCache<K,V>{
	
    //Shared resource that needs protection  
    private Map<K, V> map;  
 
    //Single instance kept  
    private ThreadSafeLocalCache<K,V> sc;  
      
    //Access method  
//    public ThreadSafeLocalCache<K,V> getInstance()   
//    {  
//    	if(sc == null){
//    		sc = new ThreadSafeLocalCache<>();
//    	}
//    	
//    	return sc;
//    }  
      
    public ThreadSafeLocalCache()   
    {  
        //ConcurrentHashMap takes care of synchronization in a a multi-threaded environment  
        map = new ConcurrentHashMap<K, V>();  
    }  
 
    public void put(K key, V value)   
    {  
        map.put(key, value);
    }  
 
    public V get(K key)   
    {  
        return map.get(key);  
    }
    
    public void remove(K key){
    	map.remove(key);
    }
    
}  
