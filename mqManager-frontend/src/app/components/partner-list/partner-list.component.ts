import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Partner } from '../../models/partner.model';
import { PartnerService } from '../../services/partner.service';
import { PartnerFormComponent } from '../partner-form/partner-form.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Page } from '../../models/page.model';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-partner-list',
  imports: [
     CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatSnackBarModule
  ],
  templateUrl: './partner-list.component.html',
  styleUrls: ['./partner-list.component.css']
})
export class PartnerListComponent implements OnInit {
  displayedColumns: string[] = ['alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];
  dataSource = new MatTableDataSource<Partner>([]);

  // Pagination properties
  totalElements: number = 0;
  pageSize: number = 10;
  pageIndex: number = 0;
  pageSizeOptions: number[] = [5, 10, 25, 50];
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadPartners();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadPartners() {
    this.partnerService.getPartners(this.pageIndex, this.pageSize).subscribe(
      (page: Page<Partner>) => {
       this.dataSource = new MatTableDataSource<Partner>(page.content);
        this.totalElements = page.totalElements;
        this.isLoading = false;
      },
      (error) => {
        console.error('Error loading partners', error);
        this.isLoading = false;
      }
    );
  }

    handlePageEvent(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPartners();
  }
  

  openPartnerForm(partner?: Partner) {
    const dialogRef = this.dialog.open(PartnerFormComponent, {
      width: '600px',
      data: partner || {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPartners();
      }
    });
  }

deletePartner(id: number): void {
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    width: '400px',
    disableClose: true
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.partnerService.deletePartner(id).subscribe({
        next: () => {
          this.loadPartners();
          // Show success message
          this.snackBar.open('Partner deleted successfully', 'Close', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top'
          });
        },
        error: (error) => {
          console.error('Error deleting partner:', error);
          this.snackBar.open('Error deleting partner', 'Close', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top'
          });
        }
      });
    }
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