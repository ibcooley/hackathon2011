//
//  StoryListViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Project;
@class Story;

@interface StoryListViewController : UIViewController <NSXMLParserDelegate, UITableViewDataSource, UITableViewDelegate> {
    
    NSMutableArray *ivStoryList;
    
    NSMutableData *ivConnectionData;
    
    NSMutableString *ivSoapString;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;
    
    Project *ivCurrentProject;
    
    Story *ivCurrentStory;
    
    UIActivityIndicatorView *ivPinwheel;

    UISegmentedControl *ivFilterSegmentedControl;
    
    UITableView *ivSummaryTableView;
    
}

@property (nonatomic, retain) NSMutableArray *StoryList;

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSMutableString *SoapString;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) Project *CurrentProject;

@property (nonatomic, retain) Story *CurrentStory;

@property (nonatomic, retain) UIActivityIndicatorView *Pinwheel;

@property (nonatomic, retain) IBOutlet UITableView *SummaryTableView;

@property (nonatomic, retain) IBOutlet UISegmentedControl *FilterSegmentedControl;

- (IBAction)FilterSegmentedControlChanged:(id)sender;

- (IBAction)NewButtonClick:(id)sender;


@end
