import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpResponse} from '@angular/common/http';
import {HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';

import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(
    private router: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // if (this.authService.authToken) {
    //   const authReq = req.clone({
    //     headers: req.headers.set(
    //       'Authorization',
    //       this.authService.authToken
    //     )
    //   });
    //   console.log('Making an authorized request');
    //   req = authReq;
    // }

    return next.handle(req).pipe(
      tap(
        event => this.handleResponse(req, event),
        error => this.handleError(req, error)
      )
    );
  }


  handleResponse(req: HttpRequest<any>, event) {    //all responses go here, including ones that had error 
    //console.log('handle response');

    //console.log('Handling response for ', req.url, event);
    if (event instanceof HttpResponse) {
      //console.log(event);
      // console.log('Request for ', req.url,
      //     ' Response Status ', event.status,
      //     ' With body ', event.body);
    }
  }

  handleError(req: HttpRequest<any>, event) {   //reponses with error go here
    //console.log('handling error');
    
    if(event.status === 401) { 
      //console.log('no auth');
      this.router.navigate(['/login']);
    }
    // console.error('Request for ', req.url,
    //       ' Response Status ', event.status,
    //       ' With error ', event.error);
  }
}