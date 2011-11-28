//
//  TaskViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Story;
@class Task;

@interface TaskViewController : UIViewController <UIPickerViewDataSource, UIPickerViewDelegate, UITableViewDataSource, UITableViewDelegate> {

    NSMutableArray *ivUsers;
    
    Story *ivThisStory;

    Task *ivThisTask;
    
    UIDatePicker *ivTaskDueDatePickerOutlet;

    UIPickerView *ivTaskStatusPickerView;
    
    UITableView *ivTaskAddUsersTableView;
    
    UITextView *ivTaskTextView;

    UISegmentedControl *EditSegmentControl;
}

@property (nonatomic, retain) NSMutableArray *Users;

@property (nonatomic, retain) Story *ThisStory;

@property (nonatomic, retain) Task *ThisTask;

@property (nonatomic, retain) IBOutlet UIDatePicker *TaskDueDatePickerOutlet;

@property (nonatomic, retain) IBOutlet UIPickerView *TaskStatusPickerView;

@property (nonatomic, retain) IBOutlet UISegmentedControl *EditSegmentControl;

@property (nonatomic, retain) IBOutlet UITableView *TaskAddUsersTableView;

@property (nonatomic, retain) IBOutlet UITextView *TaskTextView;

- (IBAction)TaskDueDatePickerChange:(id)sender;
- (IBAction)DateChanged:(id)sender;
- (IBAction)EditSegmentControlValueChanged:(id)sender;

@end
