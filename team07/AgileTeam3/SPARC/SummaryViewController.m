//
//  SummaryViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "SummaryViewController.h"

#import "Project.h"
#import "ProjectListViewController.h"
#import "ProjectViewController.h"
#import "StatusListItem.h"
#import "User.h"

@implementation SummaryViewController

@synthesize ConnectionData = ivConnectionData;
@synthesize Parser = ivParser;
@synthesize Pinwheel = ivPinwheel;
@synthesize SoapString = ivSoapString;
@synthesize SummaryList = ivSummaryList;
@synthesize TableView = ivTableView;
@synthesize TheConnection = ivTheConnection;
@synthesize ThisUser = ivThisUser;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        NSMutableArray *array = [[NSMutableArray alloc] init];
        [self setSummaryList:array];
        [array release];
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
    // Do any additional setup after loading the view from its nib.
    //Create an instance of activity indicator view
    UIActivityIndicatorView * activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 20, 20)];
    //set the initial property
    [activityIndicator stopAnimating];
    [activityIndicator hidesWhenStopped];
    //Create an instance of Bar button item with custome view which is of activity indicator
    UIBarButtonItem * barButton = [[UIBarButtonItem alloc] initWithCustomView:activityIndicator];
    //Set the bar button the navigation bar
    [[self navigationItem] setRightBarButtonItem:barButton];
    //Memory clean up
    [activityIndicator startAnimating];
    [self setPinwheel:activityIndicator];
    [activityIndicator release];
    [barButton release];
    
    [[self navigationController] setNavigationBarHidden:NO animated:NO];
    [self setTitle:[[self ThisUser] UserName]];
    // Web Service Call
    NSString *soapMessage = [NSString stringWithFormat:@"<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                             @"<s:Header />"
                             @"<s:Body>"
                             @"<GetStatusList xmlns=\"http://tempuri.org/\">"
                             @"<user>%@</user>"
                             @"</GetStatusList>"
                             @"</s:Body>"
                             @"</s:Envelope>", [[self ThisUser] UserName]];
    NSLog(@"%@", [soapMessage stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSString *fullAddress = @"http://216.69.156.62/AgileService/Service1.svc";
    NSURL *url = [NSURL URLWithString:fullAddress];
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
    NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];              
    [theRequest addValue: @"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];       
    [theRequest addValue: msgLength forHTTPHeaderField:@"Content-Length"];
    [theRequest addValue: @"http://tempuri.org/IService1/GetStatusList" forHTTPHeaderField:@"Soapaction"];
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
   
}

- (void)parserDidEndDocument:(NSXMLParser *)parser
{
    for (StatusListItem *item in [self SummaryList]) {
        NSLog(@"%@", [item description]);
    }
    [[self Pinwheel] stopAnimating];
}


- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)dealloc {
    [super dealloc];
}

#pragma mark UITableView Data Source 
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self SummaryList] count] + 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell"];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"Cell"];
    }
    // Set up the cell
    switch ([indexPath row]) {
        case 0:
            // New Project Cell
            [[cell textLabel] setText:@"Add New Project"];
            [[cell imageView] setImage:[UIImage imageNamed:@"AddCellIconGreen.png"]];
            break;
        case 1:
            // Existing Projects Cell
            [[cell textLabel] setText:@"View Existing Projects"];
            [[cell imageView] setImage:[UIImage imageNamed:@"ProjectListIcon.png"]]; 
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Project *thisProject = [[self SummaryList] objectAtIndex:index];
            [[cell textLabel] setText:[thisProject Title]];
        }
            break;
    }
    return cell;
}

#pragma mark UITableView Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    switch ([indexPath row]) {
        case 0:
            // New Project Cell
        {
            [tableView deselectRowAtIndexPath:indexPath animated:YES];
            ProjectViewController *detailVC = [[ProjectViewController alloc] initWithNibName:@"ProjectViewController" bundle:nil];
            [[self navigationController] pushViewController:detailVC animated:YES];
        }
            break;
        case 1:
            // Existing Projects Cell
        {
            ProjectListViewController *detailVC = [[ProjectListViewController alloc] initWithNibName:@"ProjectListViewController" bundle:nil];
            [[self navigationController] pushViewController:detailVC animated:YES];
            [detailVC release];
        }
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Project *thisProject = [[self SummaryList] objectAtIndex:index];
            // Push Into THIS Existing Project's StoryList VC
        }
            break;
    }
}

@end
