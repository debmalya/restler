/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reslter;

/**
 * @author debmalyajash
 *
 */
public class DJ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(28/128);
		System.out.println((double)28/128);
		String DAConsumptionValue = "0.0|0.0|11.2435|0.0";
		String DA2strValue= DAConsumptionValue.split("\\|")[2];
		double DA2DoubleValue= Double.valueOf(DA2strValue);
		double total=DA2DoubleValue*((double)28/128);
		System.out.println("total= "+ total);

	}

}
