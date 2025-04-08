import { Component, Inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Message } from '../../models/message.model';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-message-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    DatePipe,
    MatCardModule,
    MatDividerModule,
    MatIconModule],
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.css']
})
export class MessageDetailComponent {
  constructor(
    public dialogRef: MatDialogRef<MessageDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public message: Message
  ) { }

  onCancel(): void {
    this.dialogRef.close();
  }
}