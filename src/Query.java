
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
public class Query  {
	private Hashtable<String, String> query_list;
	private String lastKey = null;
	
	public Query() {
		query_list = new Hashtable<>();
	}
	
	public Query(String raw ) {
		query_list = new Hashtable<>();
	}
        public boolean contain (String key){
            if (query_list.contains(key))
                return true;
            return false;
        }
	
	public void newQuery(String key, String value) {
	
            if(!query_list.containsKey(key))
                query_list.put(key, value);
	}
	
	public String get(String key) {
		return query_list.get(key);
	}

        @Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		Iterator<Map.Entry<String,String>> it = query_list.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
			String key = StringEscapeUtils.escapeHtml4(pairs.getKey());
			String value = StringEscapeUtils.escapeHtml4(pairs.getValue());
			builder.append(key)
				.append("=")
				.append(value);
			if(it.hasNext())
				builder.append("&");
		}
		
		return builder.toString();
	}

       
        
}
