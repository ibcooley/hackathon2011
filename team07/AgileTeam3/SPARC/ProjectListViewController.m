//
//  ProjectListViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "ProjectListViewController.h"

#import "Project.h"
#import "StoryListViewController.h"

@implementation ProjectListViewController

@synthesize ConnectionData = ivConnectionData;
@synthesize CurrentProject = ivCurrentProject;
@synthesize Parser = ivParser;
@synthesize Pinwheel = ivPinwheel;
@synthesize ProjectList = ivProjectList;
@synthesize SoapString = ivSoapString;
@synthesize TheConnection = ivTheConnection;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
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
    // Do any additional setup after loading the view from its nib.
    NSMutableArray *array = [[NSMutableArray alloc] init];
    [self setProjectList:array];
    [array release];
    //Create an instance of activity indicator view
    UIActivityIndicatorView * activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 20, 20)];
    //set the initial property
    [activityIndicator stopAnimating];
    [activityIndicator hidesWhenStopped];
    //Create an instance of Bar button item with custome view which is of activity indicator
    UIBarButtonItem * barButton = [[UIBarButtonItem alloc] initWithCustomView:activityIndicator];
    [[self navigationItem] setRightBarButtonItem:barButton];
    [activityIndicator startAnimating];
    [self setPinwheel:activityIndicator];
    [activityIndicator release];
    [barButton release];
    
    [[self navigationController] setNavigationBarHidden:NO animated:NO];
    [self setTitle:@"Projects"];
    // Web Service Call
    NSString *soapMessage = @"<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                            @"<s:Header />"
                            @"<s:Body>"
                            @"<GetFullProjectList xmlns=\"http://tempuri.org/\" />"
                            @"</s:Body>"
                            @"</s:Envelope>";
    NSLog(@"%@", [soapMessage stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSString *fullAddress = @"http://216.69.156.62/AgileService/Service1.svc";
    NSURL *url = [NSURL URLWithString:fullAddress];
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
    NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];              
    [theRequest addValue: @"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];       
    [theRequest addValue: msgLength forHTTPHeaderField:@"Content-Length"];
    [theRequest addValue: @"http://tempuri.org/IService1/GetFullProjectList" forHTTPHeaderField:@"Soapaction"];
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

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [[self ProjectList] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    // Configure the cell...
    Project *thisProject = [[self ProjectList] objectAtIndex:[indexPath row]];
    [[cell textLabel] setText:[thisProject Title]];
    [[cell imageView] setImage:[UIImage imageNamed:@"ProjectListIcon.png"]]; 
    return cell;
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
    if ([elementName isEqualToString:@"ProjectItem"]) {
        Project *project = [[Project alloc] init];
        [self setCurrentProject:project];
        [project release];
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
     if ([elementName isEqualToString:@"Title"]) {
        [[self CurrentProject] setTitle:[self SoapString]];
        [[self ProjectList] addObject:[self CurrentProject]];
        [[self tableView] reloadData];
    }
}

- (void)parserDidEndDocument:(NSXMLParser *)parser
{
    for (Project *project in [self ProjectList]) {
        NSLog(@"%@", [project description]);
    }
    [[self Pinwheel] stopAnimating];
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    Project *selectedProject = [[self ProjectList] objectAtIndex:[indexPath row]];
    StoryListViewController *detailVC = [[StoryListViewController alloc] initWithNibName:@"StoryListViewController" bundle:nil];
    [detailVC setCurrentProject:selectedProject];
    [[self navigationController] pushViewController:detailVC animated:YES];
}

@end
