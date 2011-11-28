//
//  LoginViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "LoginViewController.h"

#import "SummaryViewController.h"
#import "User.h"

@implementation LoginViewController

@synthesize ConnectionData = ivConnectionData;
@synthesize Parser = ivParser;
@synthesize LoginButton = ivLoginButton;
@synthesize LoginUser = ivLoginUser;
@synthesize Pinwheel = ivPinwheel;
@synthesize SoapString = ivSoapString;
@synthesize TheConnection = ivTheConnection;
@synthesize UserNameTextField = ivUserNameTextField;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setTitle:@"Login"];
    // Do any additional setup after loading the view from its nib.
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(textFieldTextChanged:) name:UITextFieldTextDidChangeNotification object:[self UserNameTextField]];
}

- (void)viewWillAppear:(BOOL)animated
{
    [[self navigationController] setNavigationBarHidden:YES animated:NO];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [[self Pinwheel] stopAnimating];
}

- (void)viewDidUnload
{
    [self setLoginButton:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)UserTextFieldEditDone:(id)sender {
}

- (void)textFieldTextChanged:(id)sender
{
    if ([[[self UserNameTextField] text] length] == 0) {
        [[self LoginButton] setEnabled:NO];
    } else {
        [[self LoginButton] setEnabled:YES];
    }
}

- (IBAction)Login:(id)sender
{
    //Create an instance of activity indicator view
    UIActivityIndicatorView * activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 37, 37)];
    //set the initial property
    [activityIndicator hidesWhenStopped];
    [activityIndicator startAnimating];
    [activityIndicator setActivityIndicatorViewStyle:UIActivityIndicatorViewStyleGray];
    [self setPinwheel:activityIndicator];
    [activityIndicator release];
    [[self Pinwheel] setFrame:CGRectMake([[self view] frame].size.width / 2.0 - 18, [[self view] frame].size.height / 2.0 - 40, 37, 37)];
    [[self view] addSubview:[self Pinwheel]];
    
    NSString *soapMessage = [NSString stringWithFormat:@"<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                             @"<s:Header />"
                             @"<s:Body>"
                             @"<GetUser xmlns=\"http://tempuri.org/\">"
                             @"<user>%@</user>"
                             @"</GetUser>"
                             @"</s:Body>"
                             @"</s:Envelope>", [[self UserNameTextField] text]];
    NSLog(@"%@", [soapMessage stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSString *fullAddress = @"http://216.69.156.62/AgileService/Service1.svc";
    NSURL *url = [NSURL URLWithString:fullAddress];
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
    NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];              
    [theRequest addValue: @"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];       
    [theRequest addValue: msgLength forHTTPHeaderField:@"Content-Length"];
    [theRequest addValue: @"http://tempuri.org/IService1/GetUser" forHTTPHeaderField:@"Soapaction"];
    [theRequest setHTTPMethod:@"POST"];     
    [theRequest setHTTPBody: [soapMessage dataUsingEncoding:NSUTF8StringEncoding]];
    NSURLConnection *theConnection = [[NSURLConnection alloc] initWithRequest:theRequest delegate:self];
    [self setTheConnection:theConnection];
    if(theConnection) {
        NSMutableData *data = [[NSMutableData alloc] init];
        [self setConnectionData:data];
        [data release];
    }
    [theConnection release];
}

#pragma mark - NSURLConnection Section
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [[self ConnectionData] appendData:data];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    NSLog(@"\n%@", [[[NSString alloc] initWithData:[self ConnectionData] encoding:NSUTF8StringEncoding] stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSXMLParser *parser = [[NSXMLParser alloc] initWithData:[self ConnectionData]];
    [parser setDelegate:self];
    [parser setShouldProcessNamespaces:YES];
    [self setParser:parser];
    [[self Parser] parse];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    NSLog(@"\n%@", [[[NSString alloc] initWithData:[self ConnectionData] encoding:NSUTF8StringEncoding] stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSLog(@"%@", [error description]);
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Could Not Connect" message:@"I'm sorry, but the system is currently unreachable. Please check your network connectivity." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    [alert show];
    [alert release];
}

#pragma mark - NSXMLParser Delegate Section
- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    if ([elementName isEqualToString:@"Email"]) {
        User *loginUser = [[User alloc] init];
        [self setLoginUser:loginUser];
        [loginUser release];
    }
    NSMutableString *string = [[NSMutableString alloc] init];
    [self setSoapString:string];
    [string release];
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
{
    [[self SoapString] appendString:string];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if ([elementName isEqualToString:@"Email"]) {
        [[self LoginUser] setEmailAddress:[self SoapString]];
    } else if ([elementName isEqualToString:@"FirstName"]) {
        [[self LoginUser] setFirstName:[self SoapString]];
    } else if ([elementName isEqualToString:@"LastName"]) {
        [[self LoginUser] setLastName:[self SoapString]];
    } else if ([elementName isEqualToString:@"UserName"]) {
        [[self LoginUser] setUserName:[self SoapString]];
    } else if ([elementName isEqualToString:@"UserType"]) {
        if ([[self SoapString] isEqualToString:@"false"]) {
            [[self LoginUser] setUserType:kCustomer];
        } else {
            [[self LoginUser] setUserType:kTeamMember];
        }
    }
}

- (void)parserDidEndDocument:(NSXMLParser *)parser
{
    if ([self LoginUser]) {
        SummaryViewController *detailVC = [[SummaryViewController alloc] initWithNibName:@"SummaryViewController" bundle:nil];
        [detailVC setThisUser:[self LoginUser]];
        [[self navigationController] pushViewController:detailVC animated:YES];
        [detailVC release];
    } else {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"User Not Found" message:@"This user name was not found in the system. Please try again." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        [alert release];
    }
    [[self UserNameTextField] setText:@""];
    [[self UserNameTextField] resignFirstResponder];
}

- (void)dealloc {
    [ivConnectionData release];
    [ivLoginButton release];
    [ivLoginUser release];
    [ivParser release];
    [ivSoapString release];
    [ivTheConnection release];
    [ivUserNameTextField release];
    [super dealloc];
}
@end
