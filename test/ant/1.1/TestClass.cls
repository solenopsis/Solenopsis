/**
* This is a description
*
* @author Some author (me@compay.com)
* @testClasses FooBar
* hello
* @testClasses Alpha theta zeta
*/
global with sharing class TestClass {
    @testClasses soWhat
	global final static Integer AGE = 30;

	/**
	* Some class 
	*  denoted here 
	*/
	global class Foo {
		WebService Integer alpha;
		WebService String beta;

		public Foo() {}

		/**
		* A constructor based on a age
		*
		* @param i The age
		*/
		public Foo(Integer i) {
			this.alpha = i;
		}
	}
}