//
//  SummaryViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class User;

@interface SummaryViewController : UIViewController <NSXMLParserDelegate, UITableViewDataSource, UITableViewDelegate> {
    
    NSMutableArray *ivSummaryList;
    
    NSMutableData *ivConnectionData;
    
    NSMutableString *ivSoapString;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;
    
    UIActivityIndicatorView *ivPinwheel;
    
    UITableView *ivTableView;
    
    User *ivThisUser;
    
}

@property (nonatomic, retain) NSMutableArray *SummaryList;

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSMutableString *SoapString;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) UIActivityIndicatorView *Pinwheel;

@property (nonatomic, retain) IBOutlet UITableView *TableView;

@property (nonatomic, retain) User *ThisUser;

@end
