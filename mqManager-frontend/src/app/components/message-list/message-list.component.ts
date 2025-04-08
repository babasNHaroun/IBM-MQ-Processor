import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Message } from '../../models/message.model';
import { MessageService } from '../../services/message.service';
import { Page } from '../../models/page.model';
import { MessageDetailComponent } from '../message-detail/message-detail.component';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTableModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatPaginator
  ],
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {
  displayedColumns: string[] = ['messageId', 'content', 'sourceApplication', 'receivedAt', 'queueName','actions'];
  dataSource = new MatTableDataSource<Message>([]);

  // Pagination properties
  totalElements: number = 0;
  pageSize: number = 10;
  pageIndex: number = 0;
  pageSizeOptions: number[] = [5, 10, 25, 50];
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadMessages();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadMessages() {
    this.isLoading = true;
    this.messageService.getMessages(this.pageIndex, this.pageSize).subscribe(
      (page: Page<Message>) => {
        this.dataSource = new MatTableDataSource<Message>(page.content);
        this.totalElements = page.totalElements;
        this.isLoading = false;
      },
      (error) => {
        console.error('Error loading messages', error);
        this.isLoading = false;
      }
    );
  }

  handlePageEvent(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadMessages();
  }
  
  viewMessageDetails(message: Message) {
    this.dialog.open(MessageDetailComponent, {
      width: '600px',
      data: message
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}