//
//  TaskViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TaskViewController : UIViewController {
    UITextView *TaskTextView;
    UIDatePicker *TaskDueDatePickerOutlet;
    UIPickerView *TaskStatusPickerView;
}

@property (nonatomic, retain) IBOutlet UITextView *TaskTextView;
@property (nonatomic, retain) IBOutlet UIPickerView *TaskStatusPickerView;
@property (nonatomic, retain) IBOutlet UIDatePicker *TaskDueDatePickerOutlet;
- (IBAction)ChangeStatusButtonClick:(id)sender;
- (IBAction)ChangeDueDateButtonClick:(id)sender;
- (IBAction)AssignUserButtonClick:(id)sender;
- (IBAction)TaskDueDatePickerChange:(id)sender;


@end
