import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatIconModule],
  template: `
    <div class="confirm-dialog">
      <h2 mat-dialog-title class="dialog-title">
        <mat-icon class="warning-icon">warning</mat-icon>
        Confirm Deletion
      </h2>
      <mat-dialog-content>
        <p>Are you sure you want to delete this partner?</p>
        <p class="warning-text">This action cannot be undone.</p>
      </mat-dialog-content>
      <mat-dialog-actions align="end">
        <button mat-button (click)="onNoClick()">Cancel</button>
        <button mat-raised-button color="warn" (click)="onYesClick()">
          <mat-icon>delete</mat-icon>
          Delete
        </button>
      </mat-dialog-actions>
    </div>
  `,
   styles: [`
    .confirm-dialog {
      padding: 0;
    }
    .dialog-title {
      background-color: #f44336;
      color: white;
      padding: 24px 16px;  
      margin: -24px -24px 24px -24px;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      text-align: center;
    }
    .warning-icon {
      font-size: 24px;
      height: 24px;
      width: 24px;
    }
    mat-dialog-content {
      padding: 20px 24px;
      min-width: 300px;
      text-align: center;
    }
    .warning-text {
      color: #f44336;
      margin-top: 8px;
      font-style: italic;
      text-align: center;
    }
    mat-dialog-actions {
      padding: 16px;
      margin: 0;
    }
    
    button {
      margin-left: 8px;
    }
  `]
})
export class ConfirmDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    this.dialogRef.close(true);
  }
}