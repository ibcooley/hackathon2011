//
//  CheckOutTableViewCell.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CheckOutTableViewCell : UITableViewCell
{
    // adding the 2 labels we want to show in the cell
    UILabel *titleLabel;
    UILabel *detailLabel;
    UIImageView *imageView;
}

// these are the functions we will create in the .m file

// gets the data from another class
-(void)setData:(NSDictionary *)dict;

// internal function to ease setting up label text
-(UILabel *)newLabelWithPrimaryColor:(UIColor *)primaryColor selectedColor:(UIColor *)selectedColor fontSize:(CGFloat)fontSize bold:(BOOL)bold;

// you should know what this is for by know
@property (nonatomic, retain) UILabel *titleLabel;
@property (nonatomic, retain) UILabel *detailLabel;
@property (nonatomic, retain) UIImageView *imageView;


@end
