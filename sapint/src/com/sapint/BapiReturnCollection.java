/**
 * 
 */
package com.sapint;

import java.util.ArrayList;

/**
 * @author vincent
 *
 */
public class BapiReturnCollection extends ArrayList<BapiReturn> {
	
	@Override
	public BapiReturn get(int i){
		return (BapiReturn) super.get(i);
	}
	
//	public boolean add(BapiReturn bapiReturn){
//		return super.add(bapiReturn);
//	}
}
