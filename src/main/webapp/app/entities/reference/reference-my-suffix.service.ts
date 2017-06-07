import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ReferenceMySuffix } from './reference-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReferenceMySuffixService {

    private resourceUrl = 'api/references';

    constructor(private http: Http) { }

    create(reference: ReferenceMySuffix): Observable<ReferenceMySuffix> {
        const copy = this.convert(reference);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(reference: ReferenceMySuffix): Observable<ReferenceMySuffix> {
        const copy = this.convert(reference);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ReferenceMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(reference: ReferenceMySuffix): ReferenceMySuffix {
        const copy: ReferenceMySuffix = Object.assign({}, reference);
        return copy;
    }
}
