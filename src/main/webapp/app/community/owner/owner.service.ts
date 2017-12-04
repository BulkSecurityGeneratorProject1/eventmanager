import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Owner } from './owner.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OwnerService {

    private resourceUrl = SERVER_API_URL + 'api/owners';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/owners';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(owner: Owner): Observable<Owner> {
        const copy = this.convert(owner);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(owner: Owner): Observable<Owner> {
        const copy = this.convert(owner);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Owner> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Owner.
     */
    private convertItemFromServer(json: any): Owner {
        const entity: Owner = Object.assign(new Owner(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Owner to a JSON which can be sent to the server.
     */
    private convert(owner: Owner): Owner {
        const copy: Owner = Object.assign({}, owner);

        copy.created = this.dateUtils.toDate(owner.created);
        return copy;
    }
}
