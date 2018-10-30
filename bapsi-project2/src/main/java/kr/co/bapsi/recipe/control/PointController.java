package kr.co.bapsi.recipe.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.bapsi.recipe.service.RecipeService;

@RestController
@RequestMapping("/point")
public class PointController {
	
	@Autowired
	private RecipeService recipeService;
	
	// 포인트 증가
	@RequestMapping(value = "/recommand",method = RequestMethod.POST)
	public Map<String, Object> recommand(@RequestBody Map<String, Object> pointMap) throws Exception {
		
		int userNo = (int)pointMap.get("userNo");
		int receipeNo =(int)pointMap.get("receipeNo");
		int receipeUserNo = (int)pointMap.get("receipeUserNo");
		
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
			
	
		recipeService.getMPoint(receipeUserNo);
		recipeService.getRPoint(receipeNo);
		recipeService.addLogUp(userNo, receipeNo, receipeUserNo);
		msg = "게시글을 추천했습니다.";
		

		int checkLike = recipeService.checkLike(receipeNo, userNo);
		int checkDown = recipeService.checkDown(receipeNo, userNo);
		map.put("msg", msg);
		map.put("checkLike", checkLike);
		map.put("checkDown", checkDown);
		return map;
		
	}
	
	// 좋아요 취소
	@RequestMapping(value = "/recommand2",method = RequestMethod.POST)
	public Map<String, Object> recommand2(@RequestBody Map<String, Object> pointMap) throws Exception {
		
		int userNo = (int)pointMap.get("userNo");
		int receipeNo =(int)pointMap.get("receipeNo");
		int receipeUserNo = (int)pointMap.get("receipeUserNo");
		
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		recipeService.deleteLog(userNo, receipeUserNo, receipeNo);
		
		msg = "게시글을 추천을 취소했습니다.";

		
		
		int checkLike = recipeService.checkLike(receipeNo, userNo);
		int checkDown = recipeService.checkDown(receipeNo, userNo);
		map.put("msg", msg);
		map.put("checkLike", checkLike);
		map.put("checkDown", checkDown);
		return map;
		
	}
	
	// 포인트 감소
	@RequestMapping(value = "/oppose",method = RequestMethod.POST)
	public Map<String, Object> oppose(@RequestBody Map<String, Object> pointMap)  throws Exception {
	
		int userNo = (int)pointMap.get("userNo");
		int receipeNo =(int)pointMap.get("receipeNo");
		int receipeUserNo = (int)pointMap.get("receipeUserNo");
		
		String msg = "";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if (recipeService.checkBoardO(userNo, receipeNo) > 0) {
			msg = "이미 싫어요한 게시글 입니다.";
		} else {
			if (recipeService.checkBoard(userNo, receipeNo) > 0) {
				msg = "이미 좋아요한  게시글 입니다.";
			} else {
				recipeService.loseMPoint(userNo);
				recipeService.loseRPoint(receipeNo);
				recipeService.addLogDown(userNo, receipeNo, receipeUserNo);
				msg = "게시글을 반대했습니다.";
			}
		}
		map.put("msg", msg);	
		
		return map;	
		
	}
	
	
}
