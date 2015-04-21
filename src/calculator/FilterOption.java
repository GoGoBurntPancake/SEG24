package calculator;

import java.util.Date;

//FilterOption is used as an argument in most of the DBCalculator functions, serving as filtering options
//hash
public class FilterOption
{
	public Date startDate;
	public Date endDate; // Notice the difference of these two between the start time & end time in server log(the bounces)!
	public String id;
	public String gender;
    public String income;
    public String age;
    public String context;
    public Integer isConversion;//Da fuk SQLite no have boolean
    public Integer bouncePages;
    public Integer bounceSeconds;
    
    public FilterOption clone()
    {
    	FilterOption fo = new FilterOption();
    	fo.startDate = startDate;
    	fo.endDate = endDate;
    	fo.id = id;
    	fo.gender = gender;
    	fo.income = income;
    	fo.age = age;
    	fo.context = context;
    	fo.isConversion = isConversion;
    	fo.bouncePages = bouncePages;
    	fo.bounceSeconds = bounceSeconds;
    	return fo;
    }
	
}
