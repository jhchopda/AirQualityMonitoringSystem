package com.tcs.aqi.controllers;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tcs.aqi.beans.Pollutant;
import com.tcs.aqi.beans.SCL;
import com.tcs.aqi.database.Testing;

@SessionAttributes({"user","userType","message","noti"})
@Scope("session")
@Controller
public class SearchController {

	
	@RequestMapping(value = "/search")
	public String search (ModelMap model){
		
		String userType= (String)model.get("userType");
		
		System.out.println(userType);
		if(userType!=null && !userType.equals("")){
			model.addAttribute("command",new SCL());
			return "OnSearch";
		}else
			return "redirect:/";
		
	}
	/*
	@RequestMapping(value = "/search")
	public String search (ModelMap model){
		
		//String userType= (String)model.get("userType");
		
		//System.out.println(userType);
		//if(true){
			model.addAttribute("command",new SCL());
			List<String> state = new ArrayList<String>();
			state.add("Gujarat");
			state.add("Mh");
			state.add("Rj");
			state.add("Mp");
			model.addAttribute("stateList",state);
			return "searchPage";
		//}else
		//	return "redirect:/";
		
	}
	
	@RequestMapping(value="/btnClk",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List> geList(){
		 List<String> city = new ArrayList<String>();
			city.add("Kutch");
			city.add("China");
			city.add("Singapore");
			city.add("Malaysia");
			
			System.out.println("enter in controller ");
			return new ResponseEntity<List>(city, HttpStatus.OK);
	}
*/
	
	@RequestMapping(value="/processsearch")
	public String onSearch(@ModelAttribute("scl")SCL scl,ModelMap model){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		Testing testing = (Testing)context.getBean("testing");
		System.out.println("Date: "+scl.getTodate()+"  "+scl.getFromdate());
		Pollutant pollutant = testing.dateAqi(scl.getState(), scl.getCity(), scl.getLocation(), Date.valueOf(LocalDate.now()),scl.getTodate(),scl.getFromdate());
		model.addAttribute("airQuality",pollutant);
		
		 Gson gson = new Gson();
         Map<String, String> chartobj = new HashMap<String, String>();
         
         chartobj.put("caption", "AQI values");
         chartobj.put("subCaption" , scl.getTodate()+"  to  "+scl.getFromdate());
         chartobj.put("xaxisname" , "dates");
         chartobj.put("yaxisname" ,"AQI");
         chartobj.put("showValues" ,"1");
         chartobj.put("placeValuesInside" ,"1");
         //chartobj.put("theme" , "fint");
         chartobj.put("rotateValues","1");
         chartobj.put("valueFontColor", "#ffffff");
         //chartobj.put("numberprefix", "$");

         //Cosmetics
         chartobj.put("baseFontColor","#333333");
         chartobj.put("baseFont", "Helvetica Neue,Arial");
         chartobj.put("captionFontSize", "14");
         chartobj.put("subcaptionFontSize", "14");
         chartobj.put("subcaptionFontBold", "0");
         chartobj.put("showborder", "0");
         chartobj.put("paletteColors", "#0075c2");
         chartobj.put("bgcolor", "#FFFFFF");
         chartobj.put("showalternatehgridcolor", "0");
         chartobj.put("showplotborder", "0");
         chartobj.put("labeldisplay", "WRAP");
         chartobj.put("divlinecolor", "#CCCCCC");
         chartobj.put("showcanvasborder", "0");
         chartobj.put("linethickness", "3");
         chartobj.put("plotfillalpha", "100");
         chartobj.put("plotgradientcolor", "");
         chartobj.put("numVisiblePlot", "7");
         chartobj.put("divlineAlpha", "100");
         chartobj.put("divlineColor", "#999999");
         chartobj.put("divlineThickness", "1");
         chartobj.put("divLineIsDashed", "1");
         chartobj.put("divLineDashLen", "1");
         chartobj.put("divLineGapLen", "1");
         chartobj.put("scrollheight", "10");
         chartobj.put("flatScrollBars", "1");
         chartobj.put("scrollShowButtons", "0");
         chartobj.put("scrollColor", "#cccccc");
         chartobj.put("showHoverEffect", "1");
      
         ArrayList dataArray = new ArrayList();
         for(int i=0; i<pollutant.getListSize(); i++){
             
             Map<String, String> lv = new HashMap<String, String>();
             //lv.put("label", pollutant.getDate().get(i).toString() );
             lv.put("value", pollutant.getDateAqi().get(pollutant.getDate().get(i)).toString());
             dataArray.add(lv);
         }
         Map<String, Object> data = new LinkedHashMap<String, Object>();
         data.put("data", dataArray);

         ArrayList dataSet = new ArrayList();
         dataSet.add(data);
         
         ArrayList catagoryArray = new ArrayList();
         for(int i=0; i<pollutant.getListSize(); i++){
             
             Map<String, String> lv = new HashMap<String, String>();
             lv.put("label", pollutant.getDate().get(i).toString() );
             //lv.put("value", pollutant.getDateAqi().get(pollutant.getDate().get(i)).toString());
             catagoryArray.add(lv);
         }
         Map<String, Object> catagory = new LinkedHashMap<String, Object>();
         catagory.put("category", catagoryArray);

         ArrayList catagories = new ArrayList();
         catagories.add(catagory);
         
         Map<String, Object> finalDataMap = new LinkedHashMap<String, Object>();  
         finalDataMap.put("chart", chartobj);
         finalDataMap.put("categories", catagories);
         finalDataMap.put("dataset", dataSet);
         model.addAttribute("finalJsondata",gson.toJson(finalDataMap));

          System.out.println("\n\n\n"+gson.toJson(finalDataMap)+"\n\n");
		return "Search";
	}
	
}
