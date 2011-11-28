
#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>
#import <CoreLocation/CoreLocation.h>

@interface ReportViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, 
													UITextViewDelegate, MFMailComposeViewControllerDelegate, 
													UIActionSheetDelegate> 
{
	IBOutlet UITableView	*_tableView;
	
	NSString			*_reportDescription;
	UITextView			*_textView;
	NSString			*_textViewString;
											
	BOOL                isNewInstanceOnView;
}

- (UITextView *)create_UITextView;

@property (nonatomic, retain) NSString *reportDescription;
@property (nonatomic, retain) UITableView *tableView;
@property (nonatomic, retain) UITextView *textView;
@property (nonatomic, retain) NSString *textViewString;
@property (nonatomic, assign) BOOL isNewInstanceOnView;
@end
