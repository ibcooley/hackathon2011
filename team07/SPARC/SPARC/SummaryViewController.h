//
//  SummaryViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SummaryViewController : UIViewController {
    UITableView *SummaryTableView;
    UISegmentedControl *FilterSegmentedControl;
}
@property (nonatomic, retain) IBOutlet UISegmentedControl *FilterSegmentedControl;

@property (nonatomic, retain) IBOutlet UITableView *SummaryTableView;
- (IBAction)NewButtonClick:(id)sender;
- (IBAction)FilterSegmentedControlChanged:(id)sender;


@end
