//
//  LoginViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LoginViewController : UIViewController <NSXMLParserDelegate> {

    NSMutableData *ivConnectionData;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;
    
    UITextField *ivUserNameTextField;
    
}

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) UITextField *UserNameTextField;

- (IBAction)UserTextFieldEditDone:(id)sender;
- (IBAction)Login:(id)sender;

@end
