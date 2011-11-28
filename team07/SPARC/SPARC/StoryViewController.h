//
//  StoryViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface StoryViewController : UIViewController {
    UITextView *StoryTextView;
    UIPickerView *StoryStatusPicker;
    UITextField *StoryTitleField;
}
@property (nonatomic, retain) IBOutlet UITextView *StoryTextView;
@property (nonatomic, retain) IBOutlet UIPickerView *StoryStatusPicker;
@property (nonatomic, retain) IBOutlet UITextField *StoryTitleField;
- (IBAction)StoryTitleFieldEndEdit:(id)sender;
- (IBAction)EditButton:(id)sender;
- (IBAction)StatusChangeButton:(id)sender;


@end
