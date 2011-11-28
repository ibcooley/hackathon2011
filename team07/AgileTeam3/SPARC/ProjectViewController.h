//
//  ProjectViewController.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Project;

@interface ProjectViewController : UIViewController {
    
    Project *ivThisProject;

    UIButton *ivTitleButton;
    
    UITextField *ivProjectTitleTextField;
    
}

@property (nonatomic, retain) Project *ThisProject;

@property (nonatomic, retain) IBOutlet UIButton *TitleButton;

@property (nonatomic, retain) IBOutlet UITextField *ProjectTitleTextField;

@end
