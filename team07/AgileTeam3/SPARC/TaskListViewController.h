//
//  TaskListViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Project;
@class Story;
@class Task;

@interface TaskListViewController : UIViewController <NSXMLParserDelegate, UITableViewDataSource, UITableViewDelegate> {

    NSMutableArray *ivTaskList;
    
    NSMutableData *ivConnectionData;
    
    NSMutableString *ivSoapString;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;

    Project *ivCurrentProject;
    
    Story *ivCurrentStory;
    
    Task *ivCurrentTask;
    
    UIActivityIndicatorView *ivPinwheel;
    
    UISegmentedControl *ivTaskListSegmentedControl;
    
    UITableView *ivTaskListTableView;
    
}

- (IBAction)TaskListSegmentedControlChanged:(id)sender;

@property (nonatomic, retain) NSMutableArray *TaskList;

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSMutableString *SoapString;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) Project *CurrentProject;

@property (nonatomic, retain) Story *CurrentStory;

@property (nonatomic, retain) Task *CurrentTask;

@property (nonatomic, retain) UIActivityIndicatorView *Pinwheel;

@property (nonatomic, retain) IBOutlet UISegmentedControl *TaskListSegmentedControl;

@property (nonatomic, retain) IBOutlet UITableView *TaskListTableView;

@end