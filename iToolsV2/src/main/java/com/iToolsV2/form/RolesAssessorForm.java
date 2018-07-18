package com.iToolsV2.form;
 
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesAssessorForm {
	private int roleAssessorID;
    private boolean active;
    private int assessorID;
    private String userName;
    private String rolesID;
    private Date createdDate;
    private Date updatedDate;
}