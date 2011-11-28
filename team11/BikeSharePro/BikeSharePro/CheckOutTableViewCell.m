//
//  CheckOutTableViewCell.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "CheckOutTableViewCell.h"

@implementation CheckOutTableViewCell

// we need to synthesize the two labels
@synthesize titleLabel, detailLabel, imageView;

- (id)initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
	if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {
		UIView *myContentView = self.contentView;
        
		self.titleLabel = [self newLabelWithPrimaryColor:[UIColor blackColor] selectedColor:[UIColor whiteColor] fontSize:14.0 bold:YES];
		self.titleLabel.textAlignment = UITextAlignmentLeft; // default
		[myContentView addSubview:self.titleLabel];
       
       self.detailLabel = [self newLabelWithPrimaryColor:[UIColor blackColor] selectedColor:[UIColor lightGrayColor] fontSize:10.0 bold:NO];
		self.detailLabel.textAlignment = UITextAlignmentLeft; // default
		[myContentView addSubview:self.detailLabel];

 		self.imageView = [[UIImageView alloc] initWithFrame:CGRectZero];
        //set contentMode to scale aspect to fit
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
		[myContentView addSubview:self.imageView];
    }
    
	return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    
	[super setSelected:selected animated:animated];
}

-(void)setData:(NSDictionary *)dict {
	self.titleLabel.text = [dict objectForKey:@"name"];
    NSString *cost = [dict objectForKey:@"cost"];
//    NSString *serial = [dict objectForKey:@"serial"];
    NSString *detailsString = [NSString stringWithFormat:@"Cost: $%@ per hour", cost];
//    NSString *serial = [dict objectForKey:@"serial"];
//    NSString *detailsString = [NSString stringWithFormat:@"Cost: $%@ per hour | Serial: %@", cost, serial];
	self.detailLabel.text = detailsString;
    
    NSString *imageName = [dict objectForKey:@"image"];
    
    NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:imageName];
    self.imageView.image = [UIImage imageWithContentsOfFile:path];
}

- (void)layoutSubviews {
    
    [super layoutSubviews];
    
    CGRect contentRect = self.contentView.bounds;
    if (!self.editing) {
        
        CGFloat boundsX = contentRect.origin.x;
		CGRect frame;
         NSInteger labelStart = 50;
		frame = CGRectMake(boundsX + labelStart + 10, 4, 250-labelStart, 20);
		self.titleLabel.frame = frame;
        
		frame = CGRectMake(boundsX + labelStart + 10, 28, 250-labelStart, 14);
		self.detailLabel.frame = frame;
        
		frame = CGRectMake(boundsX + 10, 10, 32, 32);
		self.imageView.frame = frame;
	}
}

- (UILabel *)newLabelWithPrimaryColor:(UIColor *)primaryColor selectedColor:(UIColor *)selectedColor fontSize:(CGFloat)fontSize bold:(BOOL)bold
{   
    UIFont *font;
    if (bold) {
        font = [UIFont boldSystemFontOfSize:fontSize];
    } else {
        font = [UIFont systemFontOfSize:fontSize];
    }
    
	UILabel *newLabel = [[UILabel alloc] initWithFrame:CGRectZero];
	newLabel.backgroundColor = [UIColor whiteColor];
	newLabel.opaque = YES;
	newLabel.textColor = primaryColor;
	newLabel.highlightedTextColor = selectedColor;
	newLabel.font = font;
    
	return newLabel;
}

- (void)dealloc {
	[titleLabel release];
	[detailLabel release];
    [imageView release];
	[super dealloc];
}

@end
