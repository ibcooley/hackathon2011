//
//  LoginViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class User;

@interface LoginViewController : UIViewController <NSXMLParserDelegate, UITextFieldDelegate> {

    NSMutableData *ivConnectionData;
    
    NSMutableString *ivSoapString;
    
    NSURLConnection *ivTheConnection;
    
    NSXMLParser *ivParser;
    
    UIActivityIndicatorView *ivPinwheel;
    
    UIButton *ivLoginButton;
    
    UITextField *ivUserNameTextField;
    
    User *ivLoginUser;
}

@property (nonatomic, retain) NSMutableData *ConnectionData;

@property (nonatomic, retain) NSMutableString *SoapString;

@property (nonatomic, retain) NSURLConnection *TheConnection;

@property (nonatomic, retain) NSXMLParser *Parser;

@property (nonatomic, retain) UIActivityIndicatorView *Pinwheel;

@property (nonatomic, retain) IBOutlet UIButton *LoginButton;

@property (nonatomic, retain) IBOutlet UITextField *UserNameTextField;

@property (nonatomic, retain) User *LoginUser;

- (IBAction)UserTextFieldEditDone:(id)sender;
- (IBAction)Login:(id)sender;

@end
