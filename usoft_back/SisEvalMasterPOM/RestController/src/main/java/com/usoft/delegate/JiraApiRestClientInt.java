package com.usoft.delegate;

import java.util.List;

import com.usoft.dtos.jira.BoardList;
import com.usoft.dtos.jira.IssueType;
import com.usoft.dtos.jira.Priority;
import com.usoft.dtos.jira.SprintList;
import com.usoft.dtos.jira.SprintReport;
import com.usoft.dtos.jira.Status;
import com.usoft.dtos.jira.UserJira;
import com.usoft.dtos.jira.VelocityChartElement;

public interface JiraApiRestClientInt {
	
	public SprintReport getSprintReport(int boardId, int sprintId);
	public List<VelocityChartElement> getVelocityChart(int boardId);
	public SprintList getSprints(int boardId);
	public BoardList getBoards();
	public List<IssueType> getIssueTypes();
	public List<Priority> getPriorities();
	public List<Status> getStatus();
	public List<UserJira> getUserJira(String email);

}
