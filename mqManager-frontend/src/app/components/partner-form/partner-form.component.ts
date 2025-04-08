import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

import { Partner, Direction, ProcessedFlowType } from '../../models/partner.model';
import { PartnerService } from '../../services/partner.service';

@Component({
  selector: 'app-partner-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './partner-form.component.html',
  styleUrls: ['./partner-form.component.css']
})
export class PartnerFormComponent implements OnInit {
  partnerForm!: FormGroup;
  isEditMode = false;
  directions = Object.values(Direction);
  processedFlowTypes = Object.values(ProcessedFlowType);

  constructor(
    private fb: FormBuilder,
    private partnerService: PartnerService,
    public dialogRef: MatDialogRef<PartnerFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Partner
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.data.id;
    this.initForm();
  }

  initForm() {
    this.partnerForm = this.fb.group({
      alias: [this.data.alias || '', Validators.required],
      type: [this.data.type || '', Validators.required],
      direction: [this.data.direction || Direction.INBOUND, Validators.required],
      application: [this.data.application || ''],
      processedFlowType: [this.data.processedFlowType || ProcessedFlowType.MESSAGE, Validators.required],
      description: [this.data.description || '', Validators.required]
    });
  }

  onSubmit() {
    if (this.partnerForm.valid) {
      const partnerData = this.partnerForm.value;
      
      if (this.isEditMode && this.data?.id) {
        // Update existing partner
        partnerData.id = this.data.id;  
        this.partnerService.updatePartner(this.data.id, partnerData).subscribe({
          next: (response) => {
            this.dialogRef.close(response);
          },
          error: (error) => {
            console.error('Error updating partner:', error);
          }
        });
      } else {
        // Create new partner
        this.partnerService.createPartner(partnerData).subscribe({
          next: (response) => {
            this.dialogRef.close(response);
          },
          error: (error) => {
            console.error('Error creating partner:', error);
          }
        });
      }
    }
}


  onCancel(): void {
    this.dialogRef.close();
  }
}