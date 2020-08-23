package com.usoft.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.delegate.JiraApiRestClientInt;
import com.usoft.dtos.jira.BoardList;
import com.usoft.dtos.jira.IssueType;
import com.usoft.dtos.jira.Priority;
import com.usoft.dtos.jira.SprintList;
import com.usoft.dtos.jira.SprintReport;
import com.usoft.dtos.jira.Status;
import com.usoft.dtos.jira.UserJira;
import com.usoft.dtos.jira.VelocityChartElement;

@CrossOrigin
@RestController
@RequestMapping("/usoftapi/jira/rest")
public class ApiJiraController implements ApiJiraControllerInt{
	
	@Autowired
	private JiraApiRestClientInt jira;

	@Override
	@GetMapping("/greenhopper/latest/rapid/charts/sprintreport")
	public SprintReport getSprintReport(@RequestParam(value = "rapidViewId", required = true) int boardId, @RequestParam(value = "sprintId", required = true) int sprintId) {
		System.out.println(">> Jira - Getting sprint report");
		return jira.getSprintReport(boardId, sprintId);
	}

	@Override
	@GetMapping("/greenhopper/1.0/rapid/charts/velocity")
	public List<VelocityChartElement> getVelocityChart(@RequestParam(value = "rapidViewId", required = true ) int boardId) {
		System.out.println(">> Jira - Getting Velocity");
		return jira.getVelocityChart(boardId);
	}

	@Override
	@GetMapping("/agile/1.0/board/{boardId}/sprint")
	public SprintList getSprints(@PathVariable(value = "boardId", required = true ) int boardId) {
		System.out.println(">> Jira - Getting sprints");
		return jira.getSprints(boardId);
	}

	@Override
	@GetMapping("/agile/1.0/board")
	public BoardList getBoards() {
		System.out.println(">> Jira - Getting Boards");
		return jira.getBoards();
	}

	@Override
	@GetMapping("/api/2/issuetype")
	public List<IssueType> getIssueTypes() {
		System.out.println(">> Jira - Getting IssueTypes");
		return jira.getIssueTypes();
	}

	@Override
	@GetMapping("/api/2/priority")
	public List<Priority> getPriorities() {
		System.out.println(">> Jira - Getting Issue priorities");
		return jira.getPriorities();
	}

	@Override
	@GetMapping("/api/2/status")
	public List<Status> getStatus() {
		System.out.println(">> Jira - Getting Statues");
		return jira.getStatus();
	}

	@Override
	@GetMapping("/api/2/user/search")
	public List<UserJira> getUserJira(@RequestParam(value = "username", required = true) String email) {
		System.out.println(">> Jira - Getting UserJira");
		List<UserJira> result = jira.getUserJira(email);
		if(result==null) {
			System.out.println(">> Jira - User with email "+email+" was not found");
		}
		
		return result;
	}

}
