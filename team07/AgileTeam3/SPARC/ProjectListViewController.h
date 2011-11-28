//
//  ProjectListViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Project;

@interface ProjectListViewController : UITableViewController <NSXMLParserDelegate> {
    
    NSMutableArray *ivProjectList;
    
    NSMutableData *ivConnectionData;
    
    NSMutableString *ivSoapString;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;
    
    Project *ivCurrentProject;
    
    UIActivityIndicatorView *ivPinwheel;
    
}

@property (nonatomic, retain) NSMutableArray *ProjectList;

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSMutableString *SoapString;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) Project *CurrentProject;

@property (nonatomic, retain) UIActivityIndicatorView *Pinwheel;

@end